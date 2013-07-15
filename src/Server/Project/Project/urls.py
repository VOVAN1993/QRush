from django.conf.urls import patterns, include, url
from Server.views import *
from Server.view_add import *
from django.contrib import admin



admin.autodiscover()

urlpatterns = patterns('',
                       # Examples:
                       # url(r'^$', 'Project.views.home', name='home'),
                       # url(r'^Project/', include('Project.foo.urls')),

                       # Uncomment the admin/doc line below to enable admin documentation:
                       # url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

                       # Uncomment the next line to enable the admin:
                       url(r'^admin/', include(admin.site.urls)),
                       url(r'^hello/$', hello),
                       url(r'^add&what=user&username=(.+)&password=(.+)&deviceid=(.+)/$', addUserHandler),
                       #url(r'^add&what=user&userid=(.+)&token=(.+)&deviceid=(.+)/$',addUserVK),
                       url(r'^check&what=username&username=(.+)/$', checkUsername),
                       url(r'^add&what=scan&username=(.+)&atoken=(.+)&code=(.+)', addScanHandler),
                       url(r'^get&what=status&username=(.+)&atoken=(.+)', getStatusHandler),
                       url(r'^update&what=money&username=(.+)&atoken=(.+)&count=(\d+)/$', updateMoney),
                       url(r'^get&what=atoken&username=(.+)&password=(.+)/$', resetTokenHandler),


                       #url for post query
                       url(r'^add/$', 'Server.view_add.add'),
                       url(r'^get/$', 'Server.view_get.get'),
                       url(r'^check/$', 'Server.view_check.check'),
                       url(r'^update/$', 'Server.view_update.update'),
)
