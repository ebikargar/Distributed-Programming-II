package it.polito.dp2.NFFG.sol1;

import java.util.Calendar;
import javax.xml.datatype.XMLGregorianCalendar;

import it.polito.dp2.NFFG.PolicyReader;
import it.polito.dp2.NFFG.VerificationResultReader;
import it.polito.dp2.NFFG.sol1.jaxb.NffgType;
import it.polito.dp2.NFFG.sol1.jaxb.ResultType;

public class MyVerificationResultReader implements VerificationResultReader {

	// ------------ VerificationResult Element-----------------------//
	private PolicyReader verification_policy;
	private Boolean verification_r;
	private String verification_r_msg;
	private XMLGregorianCalendar verification_t;
	// ---------------------------------------------------------//

	public MyVerificationResultReader(NffgType nffg, ResultType result_r, MyPolicyReader policy_r) {
		this.verification_policy = policy_r;
		this.verification_r = result_r.isVerificationResult();
		this.verification_r_msg = result_r.getVerificationMessage();
		this.verification_t = result_r.getVerificationTime();

		System.out.println("MyVerificationResultReader fulfilled correctly");
	}

	// ---------------------------------------------------------//
	@Override
	public PolicyReader getPolicy() {
		if (verification_policy != null)
			return verification_policy;
		else {
			System.out.println("verification_policy Object is Null");
			return null;
		}
	}

	@Override
	public Boolean getVerificationResult() {
		if (verification_r != null)
			return verification_r;
		else {
			System.out.println("VerificationResult Object is Null");
			return null;
		}
	}

	@Override
	public String getVerificationResultMsg() {
		if (verification_r_msg != null)
			return verification_r_msg;
		else {
			System.out.println("VerificationResultMsg Object is Null");
			return null;
		}
	}

	@Override
	public Calendar getVerificationTime() {
		if (verification_t != null) {
			Calendar verification_time = verification_t.toGregorianCalendar();
			return verification_time;
		} else {
			System.out.println("VerificationTime Object is Null");
			return null;
		}
	}
}
