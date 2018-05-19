package dataHandlers;

import com.postcode.io.PostCodeDetails;
import com.postcode.io.initializers.PostcodeLookup;
public class GeoCoder {
	// 0 is longitude, 1 is latitude
	public static double[] geoCode(String postcode) throws Exception{
		PostcodeLookup a = PostcodeLookup.postcode(postcode).build();
		PostCodeDetails b = PostCodeDetails.generate(a);
		return new double[]{b.getLongitude(), b.getLatitude()};
	}
}