#!/usr/bin/env python2
# -*- coding: utf-8 -*-
# author: chengwen.li
import os, sys, argparse
import pymongo
from pylib.sysinfo import SysInfo

parser = argparse.ArgumentParser()
parser.add_argument("-p", "--profile",
        dest="profile", required=False,
        help="各环境对应的profile")

class ZooKeeperPushHistory:
    def __init__(self):
        mongourl = 'mongodb://festival:operation@192.168.160.76:27017,192.168.160.77:27017,192.168.160.78:27017/FestivalOperation'
        self.mng = pymongo.MongoClient(mongourl)
        self.db = self.mng["FestivalOperation"]
        self.coll = self.db['ZooKeeperPushHistory']

    def start(self, profile, target='ALL'):
        if target is None:
            target = 'ALL'
        entity = SysInfo().__dict__
        entity['profile'] = profile
        entity['target'] = target
        entity['start'] = SysInfo.get_current_time()
        return self.coll.insert(entity)

    def end(self, history_id, successed=True):
        self.coll.update({ '_id' : history_id }, { '$set' : { 'end' : SysInfo.get_current_time(), 'successed': successed } })
        self.mng.disconnect()

if __name__ == '__main__':
    args = parser.parse_args()
    coll_history = ZooKeeperPushHistory().coll
    query = {}
    if args.profile is not None:
        query['profile'] = args.profile
    for history in coll_history.find(query).sort('start', -1).limit(20):
        if not history.has_key('successed'):
            history['result'] = 'unkown'
        elif history['successed']:
            history['result'] = 'successed'
        else:
            history['result'] = 'failed'
        if not history.has_key("end"):
            history['end'] = 'N/A'
        print "[%(user)s] from [%(hostname)s(%(ip)s)] pushed [%(target)s] of profile[%(profile)s], start at [%(start)s], finished at [%(end)s], [%(result)s]" % history



