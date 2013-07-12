from django.db import models

class AToken(models.Model):
    #person = models.ForeignKey(User)
    atoken = models.CharField(max_length=50)
    produce = models.DateTimeField()

    def __unicode__(self):
        return self.atoken

class User(models.Model):
    username = models.CharField(max_length=50,null=True,blank=True)
    user_id = models.CharField(max_length=50,null=True,blank=True)
    balance = models.PositiveIntegerField(null=False,blank=False,default=0)
    count_scan = models.PositiveSmallIntegerField(null=False,blank=False,default=0)
    count_rescan = models.PositiveSmallIntegerField(null=False,blank=False,default=0)
    password = models.CharField(max_length=50,null=True,blank=True)
    device_id = models.CharField(max_length=50)
    token = models.ForeignKey(AToken)

    def __unicode__(self):
        if self.username is not None:
            return self.username
        if self.user_id is not None:
            return self.user_id
        return self.device_id

class Scan(models.Model):
    code = models.CharField(max_length=50)
    users = models.ManyToManyField(User)

    def __unicode__(self):
        return self.code

