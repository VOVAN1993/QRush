__author__ = "Prihodko Vladimir"

from django.http import HttpResponse
from Server.models import *

def hello(request):
    return HttpResponse("Privet")

def checkUsername(request,_username):
    if checkName(_username):
        answer = '{"report" : "success","explanation" : "User already exists"}'
    else:
        answer = '{"report" : "success","explanation" : "Login is vacant"}'
    return HttpResponse(answer)

def addUser(request,_username,_password,_device_id):
    if checkName(_username):
        answer = '{"report" : "error", "explanation" : "User already exists"}'
    else:
        new_user = User(username = _username, password = _password,balance=0,device_id=_device_id)
        new_user.save()
        answer = '{"report" : "success" , "explanation": "New user was added"}'
    return HttpResponse(answer)

def addUserVK(request,_userid,_token,_device_id):
    new_user = User(user_id=_userid,token=_token,balance=0,device_id=_device_id)
    new_user.save()
    answer = '{"report" : "success", "explanation" : "New user was added"}'
    return HttpResponse(answer)

def addScan(request,_username,_password,_code):
    user = User.objects.filter(username=_username)
    if user.count()==0:
        answer = '{"report":"error" ,"explanation" : "The user with such name doesn\'t exist"}'
        return HttpResponse(answer)
    if _password != user[0].password:
        answer = '{"report" : "error","explanation" : "Invalid password"}'
        return HttpResponse(answer)
    if Scan.objects.filter(code=_code).count()==0 :
        u = user[0]
        u.count_scan += 1
        u.save()
        s = Scan(code=_code)
        s.save()
        s.users.add(u)
        s.save()
        answer = '{"report" : "success","explanation" : "New scan was added"}'
    else:
        if Scan.objects.filter(users=user[0],code=_code).count() != 0:
            answer = '{"report" : "error", "explanation" : "This user has this scan"}'
            return HttpResponse(answer)
        s = Scan.objects.get(code=_code)
        s.users.add(user[0])
        s.save()
        answer = '{"report" : "success", "explanation" : "This scan is added to the user"}';
    return HttpResponse(answer)

def getStatus(request,_username,_password):
    if checkName(_username) is False :
        answer = '{"report" : "error", "explanation" : "The user with such name doesn\'t exist"}'
        return answer
    if checkPassword(_username,_password) is True:
        u = User.objects.get(username=_username)
        answer = '{"report" : "success", "money":"' + u.balance.__str__()\
                 +'", "count_scan":"'+ u.count_scan.__str__()\
                 +'", "count_rescan":"' + u.count_rescan.__str__()+'" }'
    else:
        answer = '{"report" : "error", "explanation" : "Invalid password"}'
    return HttpResponse(answer)

def updateMoney(request,_username,_password,_count):
    if checkName(_username) is False :
        answer = '{"report" : "error", "explanation" : "The user with such name doesn\'t exist"}'
        return answer
    else:
        if checkPassword(_username,_password) is True:
            mmax = min(50,int(_count))
            u = User.objects.get(username = _username)
            u.balance += mmax
            u.save()
            answer = '{"report" : "success", "explanation" : "Balance is changed"}'
        else:
            answer = '{"report": "error", "explanation" : "Invalid password"}'
        return HttpResponse(answer)

def checkPassword(_username,_password):
    u = User.objects.get(username=_username)
    return u.password==_password

def checkName(_name):
    return User.objects.filter(username = _name).__len__()!=0