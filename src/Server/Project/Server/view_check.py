from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from Server.util import *

__author__ = "Vladimir Prihodko"

@csrf_exempt
def check(request):
    if request.POST.get("what") == "username":
        username = None if request.POST.get("username") is None else request.POST.get("username").__str__().strip()
        userid = None if request.POST.get("userid") is None else request.POST.get("userid").__str__().strip()
        if username is None and userid is None:
            answer = '{"error":"illegal argument"}'
            return HttpResponse(answer)
        answer = checkUsername(_username=username) if username is not None else checkUsername(_userid=userid)
    return HttpResponse(answer)

@csrf_exempt
def checkUsername(_username=None,_userid=None):
    bool_ans = checkName(_username=_username) if _username is not None else checkName(_userid=_userid)
    if bool_ans:
        answer = '{"report" : "success","explanation" : "User already exists"}'
    else:
        answer = '{"report" : "success","explanation" : "Login is vacant"}'
    return answer