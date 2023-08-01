/**
* (./) udp.pde - how to use UDP library as unicast connection
* (cc) 2006, Cousot stephane for The Atelier Hypermedia
* (->) http://hypermedia.loeil.org/processing/
*
* Create a communication between Processing<->Pure Data @ http://puredata.info/
* This program also requires to run a small program on Pd to exchange data
* (hum!!! for a complete experimentation), you can find the related Pd patch
* at http://hypermedia.loeil.org/processing/udp.pd
*
* -- note that all Pd input/output messages are completed with the characters
* ";\n". Don't refer to this notation for a normal use. --
*/

// import UDP library
import hypermedia.net.*;

UDP udp; // define the UDP object
//compose the send text message
byte [] MototrboTextMessage = {0,0x10,(byte)0xA0,0,(byte)0x82,0x4,(byte)0xD,0,(byte)0xA,0,0x54,0,0x45,0,0x53,0,0x54,0};

/**
* init
*/

void setup() {
  // create a new datagram connection on port 6000
  // and wait for incomming message
  udp = new UDP( this);
  //udp.log( true ); // <-- printout the connection activity
  udp.listen( true );
}

//process events
void draw() {
;
}

/**
- To perform any action on datagram reception, you need to implement this
- handler in your code. This method will be alnutomatically called by the UDP
- object each time he receive a nonnull message.
- By default, this method have just one argument (the received message as
- byte[] array), but in addition, two arguments (representing in order the
- sender IP address and his port) can be set like below.
*/

// void receive( byte[] data ) { // <-- default handler
void receive( byte[] data, String ip, int port ) { // <-- extended handler
  byte receivebyte = 0;
  String receivestring = "";
  int index;
  println( "receive from "+ip+" on port "+port );

  if( port == 4007){
    println("Mototrbo TextMessage receive with no Ack: ");
    // convert the receive data to string and print it out
    for( index = 0; index < data.length; index ++)
    {
      receivebyte = data[index];
      if ((receivebyte<128) && (receivebyte>33))
      {
      receivestring = receivestring + char (receivebyte);;
      }
    }
    println(receivestring);
  }
}

void mouseClicked() {
  String ip = "12.0.0.1"; // the remote IP address
  int port = 4007; // the destination port
  byte sendbyte = 0;
  String sendstring = "";
  int index;
  // send the text message to remote radio
  udp.send( MototrboTextMessage, ip, port );
  println( "sent to "+ip+" on port "+port );
  println("Mototrbo text message sent:");

  // convert the send data to string and print it out
  for( index = 0; index < MototrboTextMessage.length; index ++){
    sendbyte = MototrboTextMessage[index];
    if ((sendbyte<128) && (sendbyte>33)){
      sendstring = sendstring + char (sendbyte);;
    }
  }
  println(sendstring);

}
