__author__ = "Prihodko Vladimir"
from django.http import HttpResponse
from Server.models import *


def hello(request):
    return HttpResponse("Privet")

def checkUsername(request,_username):
    if checkName(_username):
        answer = '{"report" : "User already exists"}'
    else:
        answer = '{"report":"Login is vacant"}'
    return HttpResponse(answer)

def addUser(request,_username,_password,_device_id):
    if checkName(_username):
        answer = '{"error" : "User already exists"}'
    else:
        new_user = User(username = _username, password = _password,balance=0,device_id=_device_id)
        new_user.save()
        answer = '{"success" : "New user was added"}'
    return HttpResponse(answer)

def addUserVK(request,_userid,_token,_device_id):
    new_user = User(user_id=_userid,token=_token,balance=0,device_id=_device_id)
    new_user.save()
    answer = '{"success" : "New user was added"}'
    return HttpResponse(answer)

def addScan(request,_username,_password,_code):
    user = User.objects.filter(username=_username)
    if user.count()==0:
        answer = '{"error":"The user with such name doesn\'t exist"}'
        return HttpResponse(answer)
    if _password != user[0].password:
        answer = '{"error":"Invalid password"}'
        return HttpResponse(answer)
    if Scan.objects.filter(code=_code).count()==0 :
        user[0].count_scan+=1
        s = Scan(code=_code)
        s.save()
        s.users.add(user[0])
        s.save()
        answer = '{"success":"New scan was added"}'
    else:
        if Scan.objects.filter(users=user[0],code=_code).count() != 0:
            answer = '{"error":"This user has this scan"}'
            return HttpResponse(answer)
        s = Scan.objects.get(code=_code)
        s.users.add(user[0])
        s.save()
        answer = '{"success":"This scan is added to the user"}';
    return HttpResponse(answer)

def checkName(_name):
    return User.objects.filter(username = _name).__len__()!=0