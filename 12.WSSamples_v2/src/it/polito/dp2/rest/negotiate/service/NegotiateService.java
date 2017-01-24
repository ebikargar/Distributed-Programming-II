package it.polito.dp2.rest.negotiate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.dp2.rest.negotiate.database.*;
import it.polito.dp2.rest.negotiate.model.Negotiate;

/*
 * class that manages negotiation objects
 */
public class NegotiateService {

		private float negmin = (float)5.0;	// the minimum value that can be negotiated
		private float negmax = (float)10.0;	// the maximum value that can be negotiated
		
		Map <Long , Negotiate> map = NegotiateDB.getMap();
		
		// get a single Negotiate object with given id
		public Negotiate getSingleNegotiate(Long id){		
			return map.get(id);		
		}
		
		// get a list of all the Negotiate objects
		public List<Negotiate> getNegotiate(){
			return new ArrayList<Negotiate>(map.values());	
		}
		
		// remove a Negotiate object
		public Negotiate remove(long id){
			return map.remove(id);
		}
		
		// create a negotiation object (performing negotiation of value)
		public Negotiate createNegotiate(Negotiate neg) {
			  if (neg==null) return null;
			  neg.setId(NegotiateDB.getNext());
			  return modifyNegotiate(neg);
		}
		
		// Modify (or create) a negotiation object (performing negotiation of value)
		public Negotiate modifyNegotiate(Negotiate neg) {
			  try {
				  if (neg==null) return null;
				  float max= neg.getMax();
				  float min = neg.getMin();
				
				if (max < negmin || min > negmax)
					return null;
				else {
					neg.setMin( new Float(Math.max(min, negmin)) );
				    neg.setMax( new Float(Math.min(max, negmax)) );
				    map.put(neg.getId(),neg);
				    
				  return neg;
				}
			  } catch (Exception e){
				  e.printStackTrace();
				  return null;
			  }
			}

		public Object getSynchObject() {
			return map;
		}
}
