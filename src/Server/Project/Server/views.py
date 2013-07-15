from django.core.serializers import json
from django.views.decorators.csrf import csrf_exempt

__author__ = "Prihodko Vladimir"

from django.http import HttpResponse
from Server.models import *
from datetime import datetime, date, time, timedelta
import random
import string
import json
from django.utils import timezone
from Server.view_add import *
from Server.view_get import *
from Server.view_update import *
from Server.view_check import *


def hello(request):
    return HttpResponse("Privet")


def checkUsername(request, _username):
    return HttpResponse(checkUsername(_username))


def addUserHandler(request, _username, _password, _device_id):
    return HttpResponse(addUser(_username,_password,_device_id))



def addUserVK(request, _userid, _token, _device_id):
    new_user = User(user_id=_userid, token=_token, balance=0, device_id=_device_id)
    new_user.save()
    answer = '{"report" : "success", "explanation" : "New user was added"}'
    return HttpResponse(answer)


def addScanHandler(request, _username, _token, _code):
    return HttpResponse(addScan(_username,_token,_code))


def getStatusHandler(request, _username, _token):
    answer = getStatus(_username,_token)
    return HttpResponse(answer)


def updateMoney(request, _username, _token, _count):
    answer = updateMoney(_username,_token,_count)
    return HttpResponse(answer)

def resetTokenHandler(request, _username, _password):
    answer = resetPassword(_username,_password)
    return HttpResponse(answer)













@csrf_exempt
def postMeth(request):
    return HttpResponse("vova")