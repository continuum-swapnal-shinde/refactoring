package continuum.cucumber.Page;

import java.util.HashMap;
import java.util.List;


import continuum.cucumber.PageDataBin.FeaturePackages;
import continuum.cucumber.PageObjectMapper.DtoConvert;

/**
 * Class to associate feature with packages list
 * 
 * **/
public class EntitlementHelper {

	private String delimiter="\\|";
	FeaturePackages fp = new FeaturePackages();
	
	public String packageFeatureMapping(List<HashMap<String, String>> data, int index) {
		
		boolean emptyFieldInclusion = data.get(index).get("ConfigEmptyFieldInclusion").equalsIgnoreCase("NA");

		if(!(data.get(index).get("feature").equalsIgnoreCase("NA"))){
			String feature=data.get(index).get("feature");
			fp.setName(feature);
		}
		
		if(!(data.get(index).get("featurePackages").equalsIgnoreCase("NA"))){
			String[] pack = data.get(index).get("featurePackages").split(delimiter);
			fp.setFeaturePackages(pack);
		}
		
		return DtoConvert.dtoToJsonStringNonEmpty(fp,emptyFieldInclusion);
	}

}
