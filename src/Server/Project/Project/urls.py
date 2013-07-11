#from django.conf.urls import patterns, include, url
from Server.views import *
from django.contrib import admin
from django.conf.urls.defaults import *
admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'Project.views.home', name='home'),
    # url(r'^Project/', include('Project.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    url(r'^admin/', include(admin.site.urls)),
    url(r'^hello/$',hello),
    url(r'^add&what=user&username=(.+)&password=(.+)&deviceid=(.+)/$',addUser),
    url(r'^add&what=user&userid=(.+)&token=(.+)&deviceid=(.+)/$',addUserVK),
    url(r'^check&what=username&username=(.+)/$',checkUsername),
    url(r'^add&what=scan&username=(.+)&password=(.+)&code=(.+)',addScan)
)
