#!/usr/bin/env python2
# -*- coding: utf-8 -*-
# author: chengwen.li
import os, sys, re
import logging

logging.basicConfig()

tops_path = os.path.abspath(sys.argv[0] + '/../../..')

def resolve_zk_config(profile = None, timeout = 30):
    zkService_properties_path = '/global/properties/zkService.properties'
    zk_config = {}
    if profile is None:
        effective_config_path = os.path.realpath('/opt/conf/tz-data')
        profile = re.sub(r'.*?\/tops-', '', effective_config_path)
        zkService_properties_path = effective_config_path + zkService_properties_path
        zkService_properties_path = os.path.realpath(zkService_properties_path)
    else:
        zkService_properties_path = tops_path + '/tz-data/tops-' + profile + zkService_properties_path
        zk_config['profile'] = profile
    if not os.path.isfile(zkService_properties_path):
        sys.exit('DIR[' + zkService_properties_path + '] not found, cannot resolve zookeeper hosts!')

    for line in open(zkService_properties_path):
        arr = line.strip().split('=')
        if len(arr) != 2 or arr[0].startswith('#'):
            continue
        zk_config[arr[0]] = arr[1]

    if 'connectionString' not in zk_config:
        sys.exit('config[connectionString] missing!')
    if 'zkBasePath' not in zk_config:
        sys.exit('config[zkBasePath] missing!')

    zk_config['timeout'] = timeout
    zk_config['properties_path'] = zkService_properties_path
    zk_config['profile'] = profile
    return type('ZkConfig', (), zk_config)

