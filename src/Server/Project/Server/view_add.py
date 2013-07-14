__author__ = "Prihodko Vladimir"

from django.core.serializers import json
from django.views.decorators.csrf import csrf_exempt
from django.http import HttpResponse
from django.utils import timezone

from Server.models import *
from Server.views import *
from Server.util import *

@csrf_exempt
def add(request):
    if request.POST.get("what") == "user":
        _username = request.POST.get("username").strip()
        _password = request.POST.get("password").strip()
        _deviceid = request.POST.get("deviceid").strip()
        answer = addUser(_username,_password,_deviceid)
    elif request.POST.get("what") == "scan":
        _username = request.POST.get("username").strip()
        _atoken = request.POST.get("atoken").strip()
        _code = request.POST.get("code").strip()
        answer = addScan(_username,_atoken,_code)
    return HttpResponse(answer)

@csrf_exempt
def addUser(_username,_password,_device_id):
    if checkName(_username):
        answer = '{"report" : "error", "explanation" : "User already exists"}'
    else:
        _atoken = AToken(atoken=getNewTokenStr(), produce=timezone.now())
        _atoken.save()
        new_user = User(username=_username, password=_password, balance=0, device_id=_device_id, token=_atoken)
        new_user.save()
        answer = '{"report" : "success" , "explanation": "New user was added","atoken":"' + _atoken.atoken + '"}'
    return answer

@csrf_exempt
def addScan(_username,_token,_code):
    user = User.objects.filter(username=_username)
    if user.count() == 0:
        answer = '{"report":"error" ,"explanation" : "The user with such name doesn\'t exist"}'
        return answer
    ret_list = checkToken(user[0], _token)
    if ret_list[0] is False:
        return ret_list[1]
    parse_json = json.loads(ret_list[1])
    add_str = '' if parse_json.__contains__("new_atoken") == False else \
        ',"new_atoken":"' + parse_json.get("new_atoken") + '"'
    if Scan.objects.filter(code=_code).count() == 0:
        u = user[0]
        u.count_scan += 1
        u.save()
        s = Scan(code=_code)
        s.save()
        s.users.add(u)
        s.save()
        answer = '{"report" : "success","explanation" : "New scan was added"' + add_str + '}'
    else:
        if Scan.objects.filter(users=user[0], code=_code).count() != 0:
            answer = '{"report" : "error", "explanation" : "This user has this scan"' + add_str + '}'
            return HttpResponse(answer)
        s = Scan.objects.get(code=_code)
        s.users.add(user[0])
        s.save()
        answer = '{"report" : "success", "explanation" : "This scan is added to the user"' + add_str + '}'
    return answer


