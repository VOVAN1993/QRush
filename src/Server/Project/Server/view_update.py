from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from Server.util import *

__author__ = "Vladimir Prihodko"
@csrf_exempt
def update(request):
    if request.POST.get("what")=="money":
        _username = request.POST.get("username").strip()
        _atoken = request.POST.get("atoken").strip()
        _count = request.POST.get("count").strip()
        answer = updateMoney(_username,_atoken,_count)
    return HttpResponse(answer)

@csrf_exempt
def updateMoney(_username,_atoken,_count):
    if checkName(_username) is False:
        answer = '{"report" : "error", "explanation" : "The user with such name doesn\'t exist"}'
        return answer
    else:
        user = User.objects.get(username=_username)
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
