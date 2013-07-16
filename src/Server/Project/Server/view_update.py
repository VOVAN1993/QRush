from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from Server.util import *

__author__ = "Vladimir Prihodko"
@csrf_exempt
def update(request):
    if request.POST.get("what")=="money":
        username = None if request.POST.get("username")is None else request.POST.get("username").__str__().strip()
        userid = None if request.POST.get("userid")is None else request.POST.get("userid").__str__().strip()
        atoken = None if request.POST.get("atoken")is None else request.POST.get("atoken").__str__().strip()
        count = None if request.POST.get("count")is None else request.POST.get("count").__str__().strip()
        if (username is None and userid is None) or (atoken is None) or (count is None):
             answer = '{"error":"illegal argument"}'
             return HttpResponse(answer)
        answer = updateMoney(_username=username,_atoken=atoken,_count=count) if username is not None else updateMoney(_userid=userid,_atoken=atoken,_count=count)
    return HttpResponse(answer)

@csrf_exempt
def updateMoney(_username=None,_userid=None,_atoken=None,_count=None):
    if (_username is not None and checkName(_username=_username) is False) or (_userid is not None and checkName(_userid=_userid)):
        answer = '{"report" : "error", "explanation" : "The user with such name doesn\'t exist"}'
        return answer
    else:
        user = User.objects.get(username=_username) if _username is not None else User.objects.get(user_id=_userid)
        ret_list = checkToken(user, _atoken)
        if ret_list[0] is False:
            return HttpResponse(ret_list[1])
        parse_json = json.loads(ret_list[1])
        add_str = '' if parse_json.__contains__("new_atoken") == False else \
            ',"new_atoken":"' + parse_json.get("new_atoken") + '"'

        mmax = min(200, int(_count))
        u = User.objects.get(username=_username)
        u.balance += mmax
        u.save()
        answer = '{"report" : "success", "explanation" : "Balance is changed"' + add_str + '}'
        return answer
