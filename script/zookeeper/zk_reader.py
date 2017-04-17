#!/usr/bin/env python2
# -*- coding: utf-8 -*-
# author: chengwen.li
import sys, argparse
import kazoo.exceptions
import zk_runner_base, zk_client
import pylib.colorful_printer as printer

parser = argparse.ArgumentParser()
parser.add_argument("-p", "--profile",
        dest="profile", required=False,
        help="各环境对应的profile")
parser.add_argument(
        dest="target", metavar="TARGET_CONFIG_DIR",
        help="ZooKeeper上配置文件（目录）的路径")

if __name__ == '__main__':
    args = parser.parse_args()
    zk_config = zk_runner_base.resolve_zk_config(args.profile)
    client = zk_client.zkClient(zk_config.connectionString, zk_config.timeout)
    break_this_time = False
    while True:
        if args.target is None:
            print 'path to read: ',
            sys.stdout.flush()
            path = sys.stdin.readline()
            path = path.strip()
        else:
            path = args.target.strip()
            break_this_time = True
        if len(path) == 0:
            continue
        if path.lower() == 'exit' or path.lower() == 'quit':
            break
        try:
            results = client.get(path)
            if results is None or len(results) < 1:
                printer.magenta('read [' + path + '] failed!')
                continue
            if results[1].numChildren == 0:
                for line in results[0].split():
                    print line
            else:
                children = client.get_children(path)
                print path
                for i, child in enumerate(children):
                    if i == len(children) - 1:
                        print u'  └── ' + child
                    else:
                        print u'  ├── ' + child
        except kazoo.exceptions.NoNodeError:
            print "get: ERROR: '%s' path does not exist" % path
        printer.green('<== path[' + path + ']')
        print ''
        if break_this_time:
            break
    client.close()


