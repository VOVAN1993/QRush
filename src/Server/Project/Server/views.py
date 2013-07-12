from django.core.serializers import json

__author__ = "Prihodko Vladimir"

from django.http import HttpResponse
from Server.models import *
from datetime import datetime, date, time, timedelta
import random
import string
import json
from django.utils import timezone

def hello(request):
    return HttpResponse("Privet")


def checkUsername(request, _username):
    if checkName(_username):
        answer = '{"report" : "success","explanation" : "User already exists"}'
    else:
        answer = '{"report" : "success","explanation" : "Login is vacant"}'
    return HttpResponse(answer)


def addUser(request, _username, _password, _device_id):
    if checkName(_username):
        answer = '{"report" : "error", "explanation" : "User already exists"}'
    else:
        _atoken = AToken(atoken=getNewTokenStr(), produce=timezone.now())
        _atoken.save()
        new_user = User(username=_username, password=_password, balance=0, device_id=_device_id,token=_atoken)
        new_user.save()
        answer = '{"report" : "success" , "explanation": "New user was added","atoken":"' + _atoken.atoken + '"}'
    return HttpResponse(answer)


def addUserVK(request, _userid, _token, _device_id):
    new_user = User(user_id=_userid, token=_token, balance=0, device_id=_device_id)
    new_user.save()
    answer = '{"report" : "success", "explanation" : "New user was added"}'
    return HttpResponse(answer)


def addScan(request, _username, _token, _code):
    user = User.objects.filter(username=_username)
    if user.count() == 0:
        answer = '{"report":"error" ,"explanation" : "The user with such name doesn\'t exist"}'
        return HttpResponse(answer)
    ret_list = checkToken(user[0],_token)
    if ret_list[0] is False:
        return HttpResponse(ret_list[1])
    parse_json = json.loads(ret_list[1])
    add_str = '' if parse_json.__contains__("new_atoken")==False else \
        ',"new_atoken":"'+parse_json.get("new_atoken")+'"'
    if Scan.objects.filter(code=_code).count() == 0:
        u = user[0]
        u.count_scan += 1
        u.save()
        s = Scan(code=_code)
        s.save()
        s.users.add(u)
        s.save()
        answer = '{"report" : "success","explanation" : "New scan was added"'+ add_str+'}'
    else:
        if Scan.objects.filter(users=user[0], code=_code).count() != 0:
            answer = '{"report" : "error", "explanation" : "This user has this scan"'+add_str+'}'
            return HttpResponse(answer)
        s = Scan.objects.get(code=_code)
        s.users.add(user[0])
        s.save()
        answer = '{"report" : "success", "explanation" : "This scan is added to the user"'+add_str+'}'
    return HttpResponse(answer)


def getStatus(request, _username, _token):
    if checkName(_username) is False:
        answer = '{"report" : "error", "explanation" : "The user with such name doesn\'t exist"}'
        return HttpResponse(answer)
    user = User.objects.get(username=_username)
    ret_list = checkToken(user,_token)
    if ret_list[0] is False:
        return HttpResponse(ret_list[1])
    parse_json = json.loads(ret_list[1])
    add_str = '' if parse_json.__contains__("new_atoken")==False else \
        ',"new_atoken":"'+parse_json.get("new_atoken")+'"'
    answer = '{"report" : "success", "money":"' + user.balance.__str__() \
                 + '", "count_scan":"' + user.count_scan.__str__() \
                 + '", "count_rescan":"' + user.count_rescan.__str__() + '"'+add_str+' }'
    return HttpResponse(answer)


def updateMoney(request, _username, _token, _count):
    if checkName(_username) is False:
        answer = '{"report" : "error", "explanation" : "The user with such name doesn\'t exist"}'
        return answer
    else:
        user = User.objects.get(username=_username)
        ret_list = checkToken(user,_token)
        if ret_list[0] is False:
            return HttpResponse(ret_list[1])
        parse_json = json.loads(ret_list[1])
        add_str = '' if parse_json.__contains__("new_atoken")==False else \
        ',"new_atoken":"'+parse_json.get("new_atoken")+'"'


        mmax = min(200, int(_count))
        u = User.objects.get(username=_username)
        u.balance += mmax
        u.save()
        answer = '{"report" : "success", "explanation" : "Balance is changed"'+add_str+'}'
        return HttpResponse(answer)


def checkPassword(_username, _password):
    u = User.objects.get(username=_username)
    return u.password == _password


def checkName(_name):
    return User.objects.filter(username=_name).__len__() != 0


def checkToken(_user, _token):
    atoken = _user.token
    if atoken.atoken != _token:
        answer = '{"report" : "error", "explanation":"Invalid token"}'
        return [False,answer]
    if timezone.now() - atoken.produce > timedelta(seconds=1800):
        newToken_str = getNewTokenStr()
        newToken = AToken(atoken=newToken_str, produce=timezone.now())
        newToken.save()
        _user.token = newToken
        _user.save()
        answer = '{"report" : "success", "new_atoken":"' + newToken_str + '"}'
    else:
        answer = '{"report" : "success"}'
    return [True,answer]


def getNewTokenStr():
    token = ''.join(random.choice(string.ascii_uppercase + string.ascii_lowercase + string.digits) for x in xrange(35))
    return token

