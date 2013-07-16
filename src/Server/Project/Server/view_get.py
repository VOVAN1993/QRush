from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from Server.util import *
import urllib2

__author__ = "Vladimir Prihodko"


@csrf_exempt
def get(request):
    if request.POST.get("what") == "status":
        username = None if request.POST.get("username") is None else str(request.POST.get("username")).strip()
        userid = None if request.POST.get("userid") is None else str(request.POST.get("userid")).strip()
        atoken = None if request.POST.get("atoken") is None else str(request.POST.get("atoken")).strip()
        if (username is None and userid is None) or (atoken is None):
            answer = '{"error":"illegal argument"}'
            return HttpResponse(answer)
        answer = getStatus(_username=username, _token=atoken) if username is not None else getStatus(_userid=userid,
                                                                                                     _token=atoken)
    elif request.POST.get("what") == "atoken":
        username = None if request.POST.get("username") is None else str(request.POST.get("username")).strip()
        userid = None if request.POST.get("userid") is None else str(request.POST.get("userid")).strip()
        password = None if request.POST.get("password") is None else str(request.POST.get("password")).strip()
        tokenvk = None if request.POST.get("atokenvk") is None else str(request.POST.get("atokenvk")).strip()
        if (username is None and userid is None) or (
            (username is not None and password is None) or (userid is not None and tokenvk is None)):
            answer = '{"error":"illegal argument"}'
            return HttpResponse(answer)
        answer = resetPassword(_username=username, _password=password) if username is not None else resetPassword(
            _userid=userid, _tokenvk=tokenvk)
    return HttpResponse(answer)


@csrf_exempt
def getStatus(_username=None, _userid=None, _token=None):
    if (_username is not None and checkName(_username=_username) is False) or (
            _userid is not None and checkName(_userid=_userid) is False):
        answer = '{"report" : "error", "explanation" : "The user with such name doesn\'t exist"}'
        return answer
    user = User.objects.get(username=_username) if _username is not None else User.objects.get(user_id=_userid)
    ret_list = checkToken(user, _token)
    if ret_list[0] is False:
        return ret_list[1]
    parse_json = json.loads(ret_list[1])
    add_str = '' if parse_json.__contains__("atoken") == False else \
        ',"atoken":"' + parse_json.get("atoken") + '"'
    answer = '{"report" : "success", "money":"' + user.balance.__str__() \
             + '", "count_scan":"' + user.count_scan.__str__() \
             + '", "count_rescan":"' + user.count_rescan.__str__() + '"' + add_str + ' }'
    return answer


@csrf_exempt
def resetPassword(_username=None, _userid=None, _password=None,_tokenvk=None):
    if (_username is not None and checkName(_username=_username) is False) or (
                _userid is not None and checkName(_userid=_userid)is False):
        answer = '{"report" : "error", "explanation" : "The user with such name doesn\'t exist"}'
        return answer
    if _username is not None and checkPassword(_username, _password) is False:
        answer = '{"report":"error","explanation":"Password is invalid"}'
        return answer
    if _userid is not None and checkVK(_userid,_tokenvk) is False:
        answer = '{"report":"error","explanation":"Password is invalid"}'
        return answer
    user = User.objects.get(username=_username) if _username is not None else User.objects.get(user_id=_userid)
    old_token = user.token;
    new_token = AToken(atoken=getNewTokenStr(), produce=timezone.now())
    new_token.save()
    user.token = new_token
    user.save()
    old_token.delete()
    answer = '{"report":"success","atoken":"' + new_token.atoken + '"}'
    return answer