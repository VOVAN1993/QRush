import ConfigParser

class iniWorker():
    def __init__(self):
        self.config = ConfigParser.ConfigParser()
        try:
            self.config.read('/home/QRushConfig/config.ini')
        except NameError:
            print "This ini-file is not found"
            raise

    def getHost(self):
        return self.config.get('database', 'host')

    def getName(self):
        return self.config.get('database', 'name')

    def getUser(self):
        return self.config.get('database', 'user')

    def getPassword(self):
        return self.config.get('database', 'password')