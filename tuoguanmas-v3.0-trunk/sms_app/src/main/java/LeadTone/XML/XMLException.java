package LeadTone.XML;


/**
 * 用于读取配置文件config.xml，解析xml，装载配置参数
 * 整个XML包下类实现类似dom4j的功能
 * 当XML解析错误时抛出的异常
 */
public class XMLException extends Exception
{

    public XMLException()
    {
    }

    public XMLException(String s)
    {
        super(s);
    }
}
