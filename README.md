# boards-network-control
Starting programs on USB sticks connected to the BMT boards over the network.

Starting a program by name:
argument 0 = the protocol used (“UDP” or “TCP”)
argument 1 = the udp broardcast address or ip address (UDP Default: 225.1.2.3)
argument 2 = the port (UDP Default: 5104; TCP default: 3125)
argument 3 (optional) = program name to start – no definition will stop programs running

Examples:

UDP
1. start “test.bmt” program using UDP:
Java -jar BMTBoardNetworkStarter.jar UDP 225.1.2.3 5104 test.bmt

2. stop programs running using UDP:
Java -jar BMTBoardNetworkStarter.jar UDP 225.1.2.3 5104

TCP/IP
1. start “test.bmt” program using TCP/IP:
Java -jar BMTBoardNetworkStarter.jar TCP 192.168.178.58 3125 test.bmt

1. stop programs running using TCP/IP:
Java -jar BMTBoardNetworkStarter.jar TCP 192.168.178.58 3125