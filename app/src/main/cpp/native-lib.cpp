#include <jni.h>
#include <string>
#include <cstdio>
#include <cstdlib>
#include <memory.h>
#include <ctime>

extern const char* __progname;

extern "C" JNIEXPORT jstring JNICALL
Java_com_mayank_security_dynamicprocessname_MainActivity_newProcessName(JNIEnv* env, jobject /* this */) {

    const char* processName[] = {
            "com.mayank.security.name0",
            "com.mayank.security.name1",
            "com.mayank.security.name2",
            "com.mayank.security.name3",
            "com.mayank.security.name4"
    };

    time_t t;
    srand((unsigned) time(&t));
    int value = rand()% (sizeof(processName)/sizeof(processName[0]));
    char* currentName = (char*)__progname;
    strcpy(currentName,processName[value]);
    currentName[strlen(processName[value])] = '\0';
    return env->NewStringUTF(currentName);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_mayank_security_dynamicprocessname_MainActivity_getProcessName(JNIEnv* env, jobject /* this */) {
    return env->NewStringUTF((char*)__progname);
}