package burp.com.burp.util;

import java.util.HashMap;

public class MemoFormatter {
  public String single_memorandom;
  HashMap<String, String> req_extracted_map;

  public MemoFormatter(final String request, String memorandom_format) {
	  this.req_extracted_map = new HashMap<String, String>(){
      {put("raw_req", request);}
    };
    this.single_memorandom = memorandom_format;
    this.single_memorandom = memorandom_format.replace("§§raw_req§§", req_extracted_map.get("raw_req"));
  }

  public void appendReferer(String url){
    this.single_memorandom = this.single_memorandom.replace("§§referred_from§§", url);
  }
  public void appendUrl(String url){
    this.single_memorandom = this.single_memorandom.replace("§§pentest_target§§", url);
  }
  public void appendMethod(String method){
    this.single_memorandom = this.single_memorandom.replace("§§method§§", method);
  }
  public void appendQueries(String query){
    this.single_memorandom = this.single_memorandom.replace("§§get_params§§", query);
  }
  public void appendBodies(String body){
    this.single_memorandom = this.single_memorandom.replace("§§body_params§§", body);
  }
  public void appendCookies(String cookies){
    this.single_memorandom = this.single_memorandom.replace("§§cookies§§", cookies);
  }
  public void appendParamsCount(int paramsCount){
    this.single_memorandom = this.single_memorandom.replace("§§params_count§§", paramsCount + "");
  }
  
  
}