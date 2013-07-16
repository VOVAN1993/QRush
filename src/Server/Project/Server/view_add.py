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
        username = None if request.POST.get("username")is None else request.POST.get("username").__str__().strip()
        userid = None if request.POST.get("userid") is None else request.POST.get("userid").__str__().strip()
        password = None if request.POST.get("password") is None else request.POST.get("password").__str__().strip()
        deviceid = None if request.POST.get("deviceid") is None else request.POST.get("deviceid").__str__().strip()
        if (username is None and userid is None) or (password is None and username is not None) or (deviceid is None):
            answer = '{"error":"illegal argument"}'
            return answer
        answer = addUser(_username=username, _password=password,
                         _device_id=deviceid) if username is not None else addUser(_userid=userid,
                                                                                   _device_id=deviceid)
    elif request.POST.get("what") == "scan":
        username = None if request.POST.get("username")is None else request.POST.get("username").__str__().strip()
        userid = None if request.POST.get("userid") is None else request.POST.get("userid").__str__().strip()
        atoken = None if request.POST.get("atoken")is None else str(request.POST.get("atoken")).strip()
        code = None if request.POST.get("code") is None else str(request.POST.get("code")).strip()
        if (code is None) or (username is None and userid is None) or (atoken is None):
             answer = '{"error":"illegal argument"}'
             return HttpResponse(answer)
        answer = addScan(_username=username, _token=atoken, _code=code) if username is not None else addScan(_userid=userid, _token=atoken, _code=code)
    return HttpResponse(answer)


@csrf_exempt
def addUser(_username=None, _userid=None, _password=None, _device_id=None):
    if _username is not None:
        if checkName(_username):
            answer = '{"report" : "error", "explanation" : "User already exists"}'
            return answer

    _atoken = AToken(atoken=getNewTokenStr(), produce=timezone.now())
    _atoken.save()
    new_user = User(username=_username, password=_password, balance=0, device_id=_device_id,
                    token=_atoken) if _username is not None else User(user_id=_userid, balance=0,
                                                                      device_id=_device_id, token=_atoken)
    new_user.save()
    answer = '{"report" : "success" , "explanation": "New user was added","atoken":"' + _atoken.atoken + '"}'
    return answer


@csrf_exempt
def addScan(_username=None,_userid=None, _token=None, _code=None):
    user = User.objects.filter(username=_username) if _username is not None else User.objects.filter(user_id=_userid)
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


