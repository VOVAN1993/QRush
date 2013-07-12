from django.contrib import admin
from Server.models import User,Scan,AToken

admin.site.register(User)
admin.site.register(Scan)
admin.site.register(AToken)