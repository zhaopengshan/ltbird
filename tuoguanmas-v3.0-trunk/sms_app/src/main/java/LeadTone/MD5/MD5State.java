package LeadTone.MD5;


/**
 * MD5算法的标准实现
 */
class MD5State
{
    int state[];
    int count[];
    byte buffer[];

    public MD5State()
    {
        buffer = new byte[64];
        count = new int[2];
        state = new int[4];
        state[0] = 0x67452301;
        state[1] = 0xefcdab89;
        state[2] = 0x98badcfe;
        state[3] = 0x10325476;
        count[0] = count[1] = 0;
    }

    public MD5State(MD5State from)
    {
        this();
        for(int i = 0; i < buffer.length; i++)
            buffer[i] = from.buffer[i];

        for(int i = 0; i < state.length; i++)
            state[i] = from.state[i];

        for(int i = 0; i < count.length; i++)
            count[i] = from.count[i];

    }


}
