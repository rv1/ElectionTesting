/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rahul
 */

import java.io.Serializable;

public class ElectionPacket implements Serializable {

	/* define packet formats */
	public static final int ELECTION_NULL    = 0;
	public static final int ELECTION_REQUEST = 100;
	public static final int ELECTION_ACK   = 200;
	public static final int ELECTION_NACK     = 300;
	
	/* the packet payload */
	
	/* initialized to be a null packet */
	public int type = ELECTION_NULL;
	
	/* send your message here */
	public String message;
	
}
