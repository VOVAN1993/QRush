from Server.models import *
import string
import random
from datetime import datetime, date, time, timedelta
import json
from django.utils import timezone

def checkName(_name):
    return User.objects.filter(username=_name.strip()).__len__() != 0

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
        answer = '{"report" : "success", "new_atoken":"' + newToken_str + '"}'
    else:
        answer = '{"report" : "success"}'
    return [True, answer]

def checkPassword(_username, _password):
    u = User.objects.get(username=_username)
    return u.password == _password