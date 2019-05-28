package com.api.util;


import java.util.ArrayList;
import java.util.List;

public class ResponseBody {
	
	
	public String Id;
	public String BrandName;
	public String LaptopName;
	public Features Features;
	
	public class Features{
		public List<String> Feature = new ArrayList<String>();
	}

}


