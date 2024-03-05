


import sliplib
import pytun
import threading
import serial
import timeout_decorator

import serial.tools.list_ports


class Faraday(object):
    


    def __init__(self, serialPort=None):
        self._serialPort = serialPort

    def send(self, msg):
        

        
        slipDriver = sliplib.Driver()

        
        slipData = slipDriver.send(msg)

        
        res = self._serialPort.write(slipData)

        
        return res

    def receive(self, length):
        


        
        slipDriver = sliplib.Driver()

        
        ret = self._serialPort.read(length)

        
        temp = slipDriver.receive(ret)
        return iter(temp)


class TunnelServer(object):
    

    def __init__(self, addr,
                 netmask,
                 mtu,
                 name):
        self._tun = pytun.TunTapDevice(name=name)
        self._tun.addr = addr
        self._tun.netmask = netmask
        self._tun.mtu = mtu

        
        self._tun.persist(True)
        self._tun.up()

    def __del__(self):
        

        self._tun.down()
        print("TUN brought down...")


class Monitor(threading.Thread):
    

    def __init__(self,
                 serialPort,
                 isRunning,
                 name="Faraday",
                 addr='10.0.0.1',
                 netmask='255.255.255.0',
                 mtu=1500):
        super().__init__()
        self.isRunning = isRunning
        self._serialPort = serialPort

        
        self._TUN = TunnelServer(name=name,
                                 addr=addr,
                                 netmask=netmask,
                                 mtu=mtu)

        
        self._faraday = Faraday(serialPort=serialPort)

    @timeout_decorator.timeout(1, use_signals=False)
    def checkTUN(self):
        

        packet = self._TUN._tun.read(self._TUN._tun.mtu)
        return(packet)

    def monitorTUN(self):
        

        packet = self.checkTUN()

        if packet:
            try:
                
                ret = self._faraday.send(packet)
                return ret

            except AttributeError as error:
                
                print("AttributeError")

    def rxSerial(self, length):
        

        return(self._faraday.receive(length))

    def txSerial(self, data):
        

        return self._faraday.send(data)

    def checkSerial(self):
        

        for item in self.rxSerial(self._TUN._tun.mtu):
            
            try:
                self._TUN._tun.write(item)
            except pytun.Error as error:
                print("pytun error writing: {0}".format(item))
                print(error)

    def run(self):
        

        while self.isRunning.is_set():
            try:
                try:
                    
                    self.monitorTUN()

                except timeout_decorator.TimeoutError as error:
                    
                    pass
                self.checkSerial()
            except KeyboardInterrupt:
                break


class SerialTestClass(object):
    

    def __init__(self):
        

        self._port = "loop://"
        self._timeout = 0
        self._baudrate = 115200
        self.serialPort = \
            serial.serial_for_url(url=self._port,
                                  timeout=self._timeout,
                                  baudrate=self._baudrate)

    def isPortAvailable(port='/dev/ttyUSB0'):
        

        isPortAvailable = serial.tools.list_ports.grep(port)

        try:
            next(isPortAvailable)
            available = True
        except StopIteration:
            available = False

        return available
