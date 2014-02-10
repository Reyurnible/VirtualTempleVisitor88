/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/oguro/web/VirtualTempleVisitor88/VirtualVisitor88/src/com/main/virtualvisitor88/walkCallback.aidl
 */
package com.main.virtualvisitor88;
public interface walkCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.main.virtualvisitor88.walkCallback
{
private static final java.lang.String DESCRIPTOR = "com.main.virtualvisitor88.walkCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.main.virtualvisitor88.walkCallback interface,
 * generating a proxy if needed.
 */
public static com.main.virtualvisitor88.walkCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.main.virtualvisitor88.walkCallback))) {
return ((com.main.virtualvisitor88.walkCallback)iin);
}
return new com.main.virtualvisitor88.walkCallback.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_updateWalkCount:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.updateWalkCount(_arg0);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.main.virtualvisitor88.walkCallback
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void updateWalkCount(int count) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(count);
mRemote.transact(Stub.TRANSACTION_updateWalkCount, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_updateWalkCount = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void updateWalkCount(int count) throws android.os.RemoteException;
}
