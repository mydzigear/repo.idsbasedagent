/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class jpcap_Jpcap */

#ifndef _Included_jpcap_Jpcap
#define _Included_jpcap_Jpcap
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     jpcap_Jpcap
 * Method:    nativeOpenLive
 * Signature: (Ljava/lang/String;III)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_jpcap_Jpcap_nativeOpenLive
  (JNIEnv *, jobject, jstring, jint, jint, jint);

/*
 * Class:     jpcap_Jpcap
 * Method:    nativeOpenOffline
 * Signature: (Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_jpcap_Jpcap_nativeOpenOffline
  (JNIEnv *, jobject, jstring);

/*
 * Class:     jpcap_Jpcap
 * Method:    lookupDevice
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_jpcap_Jpcap_lookupDevice
  (JNIEnv *, jclass);

/*
 * Class:     jpcap_Jpcap
 * Method:    getPacket
 * Signature: ()Ljpcap/Packet;
 */
JNIEXPORT jobject JNICALL Java_jpcap_Jpcap_getPacket
  (JNIEnv *, jobject);

/*
 * Class:     jpcap_Jpcap
 * Method:    processPacket
 * Signature: (ILjpcap/JpcapHandler;)I
 */
JNIEXPORT jint JNICALL Java_jpcap_Jpcap_processPacket
  (JNIEnv *, jobject, jint, jobject);

/*
 * Class:     jpcap_Jpcap
 * Method:    setFilter
 * Signature: (Ljava/lang/String;Z)V
 */
JNIEXPORT void JNICALL Java_jpcap_Jpcap_setFilter
  (JNIEnv *, jobject, jstring, jboolean);

/*
 * Class:     jpcap_Jpcap
 * Method:    updateStat
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jpcap_Jpcap_updateStat
  (JNIEnv *, jobject);

/*
 * Class:     jpcap_Jpcap
 * Method:    openRawSocket
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jpcap_Jpcap_openRawSocket
  (JNIEnv *, jobject);

/*
 * Class:     jpcap_Jpcap
 * Method:    sendPacket
 * Signature: (Ljpcap/IPPacket;[B)V
 */
JNIEXPORT void JNICALL Java_jpcap_Jpcap_sendPacket
  (JNIEnv *, jobject, jobject, jbyteArray);

/*
 * Class:     jpcap_Jpcap
 * Method:    getErrorMessage
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_jpcap_Jpcap_getErrorMessage
  (JNIEnv *, jobject);

/*
 * Class:     jpcap_Jpcap
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_jpcap_Jpcap_close
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif