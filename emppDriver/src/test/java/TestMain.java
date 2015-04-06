import java.io.UnsupportedEncodingException;

import empp2.util.ByteUtil;


public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] arg) {
		// TODO Auto-generated method stub
		String encSms ="4e1a52a17c7b578bff1a5ba2623767658bbf75338bf7ff0c67658bbf535553f7ff1a00430056003200300031003400310032003100380030003000300031ff0c987976eeff1a88625dde51437acb91d15c5e523654c167099650516c53f8ff0c59d3540dff1a80e173b2ff0c75358bddff1a00310035003000300035003700300037003200390036ff0c8ba1521252308bbf65e5671fff1a0032003000310034002f00310032002f00310038ff0c5df27ecf901a8fc7529e4e8b59045ba16279ff0c8bf753ca65f652066d3e63a55f854efb52a1ff0c8fdb884c76f8517351c6590730104e0a6d7751ef6cc96cf54e1a002896c656e2002967099650516c53f83011";
		try {
			System.out.println( new String( ByteUtil.hex2byte(encSms),"UnicodeBigUnmarked") );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
