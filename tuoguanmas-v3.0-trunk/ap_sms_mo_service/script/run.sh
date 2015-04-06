#!/bin/bash
        if [ x$SMS_HOME = x  ]; then
                export SMS_HOME=/var/pingtai/ap_sms_mo_service
        fi

        SMS_CONF=$SMS_HOME/conf
        SMS_BIN=$SMS_HOME/bin
        SMS_LIB=$SMS_HOME/lib

        CLASSPATH=$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar:$SMS_LIB
        cd $SMS_LIB
        for l in `ls`
        do
                CLASSPATH=$CLASSPATH:$SMS_LIB/$l
        done

        CLASSPATH=$CLASSPATH:$SMS_CONF

        
        LOG_CONF_FILE=${MIG_BASE}/conf/log4j.xml

        ulimit -n 10240

		#echo $CLASSPATH

        $JAVA_HOME/bin/java -Dfile.encoding=UTF8 -classpath  .:$CLASSPATH com.leadtone.mas.bizplug.smsmo.service.SmsMoService 1>$SMS_HOME/log/1.out 2>$SMS_HOME/log/2.out
		#1>/dev/null 2>/dev/null
