from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from Server.util import *

__author__ = "Vladimir Prihodko"


@csrf_exempt
def get(request):
    if request.POST.get("what") == "status":
        _username = request.POST.get("username").strip()
        _atoken = request.POST.get("atoken").strip()
        answer = getStatus(_username,_atoken)
    elif request.POST.get("what") == "atoken":
        _username = request.POST.get("username").strip()
        _password = request.POST.get("password").strip()
        answer = resetPassword(_username,_password)
    return HttpResponse(answer)

@csrf_exempt
def getStatus(_username,_token):
    if checkName(_username) is False:
        answer = '{"report" : "error", "explanation" : "The user with such name doesn\'t exist"}'
        return answer
    user = User.objects.get(username=_username)
    ret_list = checkToken(user, _token)
    if ret_list[0] is False:
        return ret_list[1]
    parse_json = json.loads(ret_list[1])
    add_str = '' if parse_json.__contains__("new_atoken") == False else \
        ',"new_atoken":"' + parse_json.get("new_atoken") + '"'
    answer = '{"report" : "success", "money":"' + user.balance.__str__() \
             + '", "count_scan":"' + user.count_scan.__str__() \
             + '", "count_rescan":"' + user.count_rescan.__str__() + '"' + add_str + ' }'
    return answer

@csrf_exempt
def resetPassword(_username,_password):
    if checkName(_username) is False:
        answer = '{"report" : "error", "explanation" : "The user with such name doesn\'t exist"}'
        return answer
    if checkPassword(_username, _password) is False:
        answer = '{"report":"error","explanation":"Password is invalid"}'
        return answer
    user = User.objects.get(username=_username)
    old_token = user.token;
    new_token = AToken(atoken=getNewTokenStr(), produce=timezone.now())
    new_token.save()
    user.token = new_token
    user.save()
    old_token.delete()
    answer = '{"report":"success","atoken":"' + new_token.atoken + '"}'
    return answer