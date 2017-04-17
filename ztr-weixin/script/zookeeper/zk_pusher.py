#!/usr/bin/env python2
# -*- coding: utf-8 -*-
# author: chengwen.li
import sys, os.path, argparse, traceback, re
import zk_runner_base as base
import pylib.colorful_printer as printer
import zk_client, zk_history

parser = argparse.ArgumentParser()
parser.add_argument("-p", "--profile",
        dest="profile", required=False,
        help="各环境对应的profile")
parser.add_argument('-t', '--target',
        dest="target", required=False, metavar="TARGET_CONFIG_DIR",
        help="要上传的配置文件（目录）的真实路径（绝对或相对都可以）。")

def push_config_dir(local_path, zk_path, zookeeper):
    local_targets = set(os.listdir(local_path))
    if not zookeeper.exists(zk_path):
        zk_targets = set()
    else:
        zk_targets = set(zookeeper.get_children(zk_path))

    targets_to_delete = zk_targets.difference(local_targets)
    for del_target in targets_to_delete:
        zookeeper.delete(zk_path + '/' + del_target, True)

    targets_to_create = local_targets.difference(zk_targets)
    for create_target in targets_to_create:
        zk_target_path = zk_path + '/' + create_target
        local_target_path = local_path + '/' + create_target
        if os.path.isdir(local_target_path):
            push_config_dir(local_target_path, zk_target_path, zookeeper)
        else:
            zookeeper.create_with_file(local_target_path, zk_target_path)

    targets_to_modify = local_targets.intersection(zk_targets)
    for modify_target in targets_to_modify:
        zk_target_path = zk_path + '/' + modify_target
        local_target_path = local_path + '/' + modify_target
        if os.path.isdir(local_target_path):
            push_config_dir(local_target_path, zk_target_path, zookeeper)
        else:
            zookeeper.modify_with_file(local_target_path, zk_target_path)


if __name__ == '__main__':
    args = parser.parse_args()
    if args.target is not None:
        target_path = os.path.abspath(args.target)
        if not target_path.startswith(base.tops_path + '/tz-data/tops-'):
            sys.exit('illegal config target!')
        if args.profile is None:
            temp = target_path[len(base.tops_path + '/tz-data/tops-'):]
            idx = temp.find('/')
            if idx >= 0:
                temp = temp[0:idx]
            args.profile = temp

    zk_config = base.resolve_zk_config(profile = args.profile)
    zookeeper = zk_client.zkClient(zk_config.connectionString, zk_config.timeout)

    local_config_root_path = os.path.abspath(zk_config.properties_path + '/../../..')
    local_config_root_path = os.path.realpath(local_config_root_path)
    if args.target is None:
        zk_root_path = zk_config.zkBasePath
        local_root_path = local_config_root_path
    else:
        local_root_path = os.path.abspath(args.target)
        local_root_path = os.path.realpath(local_root_path)
        if not local_root_path.startswith(local_config_root_path):
            sys.exit('invalid config path!')
        zk_root_path = zk_config.zkBasePath + '/' + local_root_path[len(local_config_root_path):]

    history = zk_history.ZooKeeperPushHistory()
    history_id = history.start(zk_config.profile, local_root_path[len(base.tops_path):]);
    try:
        if os.path.isfile(local_root_path):
            if zookeeper.exists(zk_root_path):
                zookeeper.modify_with_file(local_root_path, zk_root_path)
            else:
                zookeeper.create_with_file(local_root_path, zk_root_path)
        else:
            push_config_dir(local_root_path, zk_root_path, zookeeper)
        history.end(history_id)
    except:
        print traceback.format_exc()
        history.end(history_id, False)
    finally:
        zookeeper.close()

