from django.db import models

class User(models.Model):
    username = models.CharField(max_length=50,null=True,blank=True)
    user_id = models.CharField(max_length=50,null=True,blank=True)
    balance = models.PositiveIntegerField(null=False,blank=True)
    count_scan = models.PositiveSmallIntegerField(null=False,blank=True,default=0)
    count_rescan = models.PositiveSmallIntegerField(null=False,blank=True,default=0)
    password = models.CharField(max_length=50,null=True,blank=True)
    device_id = models.CharField(max_length=50,null=False,blank=True)
    token = models.CharField(max_length=50,null=True,blank=True)

class Scan(models.Model):
    code = models.CharField(max_length=50,null=False,blank=True)
    users = models.ManyToManyField(User)