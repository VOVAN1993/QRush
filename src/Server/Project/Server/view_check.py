from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from Server.util import *

__author__ = "Vladimir Prihodko"

@csrf_exempt
def check(request):
    if request.POST.get("what") == "username":
        _username = request.POST.get("username").strip()
        answer = checkUsername(_username)
    return HttpResponse(answer)

@csrf_exempt
def checkUsername(_username):
    if checkName(_username):
        answer = '{"report" : "success","explanation" : "User already exists"}'
    else:
        answer = '{"report" : "success","explanation" : "Login is vacant"}'
    return answer