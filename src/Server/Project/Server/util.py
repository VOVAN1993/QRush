from Server.models import *
import string
import random
from datetime import datetime, date, time, timedelta
import json
from django.utils import timezone
import urllib2

def checkName(_username=None,_userid=None):
    return User.objects.filter(username=_username.strip()).__len__() != 0 if _username is not None else User.objects.filter(user_id=_userid.strip()).__len__() != 0

def getNewTokenStr():
    token = ''.join(random.choice(string.ascii_uppercase + string.ascii_lowercase + string.digits) for x in xrange(35))
    return token

def checkToken(_user, _token):
    atoken = _user.token
    if atoken.atoken != _token:
        answer = '{"report" : "error", "explanation":"Invalid token"}'
        return [False, answer]
    if timezone.now() - atoken.produce > timedelta(seconds=1800):
        newToken_str = getNewTokenStr()
        newToken = AToken(atoken=newToken_str, produce=timezone.now())
        newToken.save()
        _user.token = newToken
        _user.save()
        answer = '{"report" : "success", "atoken":"' + newToken_str + '"}'
    else:
        answer = '{"report" : "success"}'
    return [True, answer]

def checkPassword(_username, _password):
    u = User.objects.get(username=_username)
    return u.password == _password

def checkVK(_userid,_tokenvk):
    req = urllib2.Request("https://api.vk.com/method/users.get?&access_token="+_tokenvk)
    res = urllib2.urlopen(req)
    st = res.read()
    answer = json.loads(str(st))
    return answer.get('response')[0].get('uid').__str__()==_userid.__str__()