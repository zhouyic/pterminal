/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package communicate.pkmgmt.service;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GetProcessService {

  public interface Iface {

    public String setProcess(String tmlId, String process) throws org.apache.thrift.TException;

  }

  public interface AsyncIface {

    public void setProcess(String tmlId, String process, org.apache.thrift.async.AsyncMethodCallback<AsyncClient.setProcess_call> resultHandler) throws org.apache.thrift.TException;

  }

  public static class Client extends org.apache.thrift.TServiceClient implements Iface {
    public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
      public Factory() {}
      public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
        return new Client(prot);
      }
      public Client getClient(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
        return new Client(iprot, oprot);
      }
    }

    public Client(org.apache.thrift.protocol.TProtocol prot)
    {
      super(prot, prot);
    }

    public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
      super(iprot, oprot);
    }

    public String setProcess(String tmlId, String process) throws org.apache.thrift.TException
    {
      send_setProcess(tmlId, process);
      return recv_setProcess();
    }

    public void send_setProcess(String tmlId, String process) throws org.apache.thrift.TException
    {
      setProcess_args args = new setProcess_args();
      args.setTmlId(tmlId);
      args.setProcess(process);
      sendBase("setProcess", args);
    }

    public String recv_setProcess() throws org.apache.thrift.TException
    {
      setProcess_result result = new setProcess_result();
      receiveBase(result, "setProcess");
      if (result.isSetSuccess()) {
        return result.success;
      }
      throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT, "setProcess failed: unknown result");
    }

  }
  public static class AsyncClient extends org.apache.thrift.async.TAsyncClient implements AsyncIface {
    public static class Factory implements org.apache.thrift.async.TAsyncClientFactory<AsyncClient> {
      private org.apache.thrift.async.TAsyncClientManager clientManager;
      private org.apache.thrift.protocol.TProtocolFactory protocolFactory;
      public Factory(org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.protocol.TProtocolFactory protocolFactory) {
        this.clientManager = clientManager;
        this.protocolFactory = protocolFactory;
      }
      public AsyncClient getAsyncClient(org.apache.thrift.transport.TNonblockingTransport transport) {
        return new AsyncClient(protocolFactory, clientManager, transport);
      }
    }

    public AsyncClient(org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.async.TAsyncClientManager clientManager, org.apache.thrift.transport.TNonblockingTransport transport) {
      super(protocolFactory, clientManager, transport);
    }

    public void setProcess(String tmlId, String process, org.apache.thrift.async.AsyncMethodCallback<setProcess_call> resultHandler) throws org.apache.thrift.TException {
      checkReady();
      setProcess_call method_call = new setProcess_call(tmlId, process, resultHandler, this, ___protocolFactory, ___transport);
      this.___currentMethod = method_call;
      ___manager.call(method_call);
    }

    public static class setProcess_call extends org.apache.thrift.async.TAsyncMethodCall {
      private String tmlId;
      private String process;
      public setProcess_call(String tmlId, String process, org.apache.thrift.async.AsyncMethodCallback<setProcess_call> resultHandler, org.apache.thrift.async.TAsyncClient client, org.apache.thrift.protocol.TProtocolFactory protocolFactory, org.apache.thrift.transport.TNonblockingTransport transport) throws org.apache.thrift.TException {
        super(client, protocolFactory, transport, resultHandler, false);
        this.tmlId = tmlId;
        this.process = process;
      }

      public void write_args(org.apache.thrift.protocol.TProtocol prot) throws org.apache.thrift.TException {
        prot.writeMessageBegin(new org.apache.thrift.protocol.TMessage("setProcess", org.apache.thrift.protocol.TMessageType.CALL, 0));
        setProcess_args args = new setProcess_args();
        args.setTmlId(tmlId);
        args.setProcess(process);
        args.write(prot);
        prot.writeMessageEnd();
      }

      public String getResult() throws org.apache.thrift.TException {
        if (getState() != org.apache.thrift.async.TAsyncMethodCall.State.RESPONSE_READ) {
          throw new IllegalStateException("Method call not finished!");
        }
        org.apache.thrift.transport.TMemoryInputTransport memoryTransport = new org.apache.thrift.transport.TMemoryInputTransport(getFrameBuffer().array());
        org.apache.thrift.protocol.TProtocol prot = client.getProtocolFactory().getProtocol(memoryTransport);
        return (new Client(prot)).recv_setProcess();
      }
    }

  }

  public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I> implements org.apache.thrift.TProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Processor.class.getName());
    public Processor(I iface) {
      super(iface, getProcessMap(new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
    }

    protected Processor(I iface, Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      super(iface, getProcessMap(processMap));
    }

    private static <I extends Iface> Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> getProcessMap(Map<String,  org.apache.thrift.ProcessFunction<I, ? extends  org.apache.thrift.TBase>> processMap) {
      processMap.put("setProcess", new setProcess());
      return processMap;
    }

    public static class setProcess<I extends Iface> extends org.apache.thrift.ProcessFunction<I, setProcess_args> {
      public setProcess() {
        super("setProcess");
      }

      public setProcess_args getEmptyArgsInstance() {
        return new setProcess_args();
      }

      protected boolean isOneway() {
        return false;
      }

      public setProcess_result getResult(I iface, setProcess_args args) throws org.apache.thrift.TException {
        setProcess_result result = new setProcess_result();
        result.success = iface.setProcess(args.tmlId, args.process);
        return result;
      }
    }

  }

  public static class setProcess_args implements org.apache.thrift.TBase<setProcess_args, setProcess_args._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("setProcess_args");

    private static final org.apache.thrift.protocol.TField TML_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("tmlId", org.apache.thrift.protocol.TType.STRING, (short)1);
    private static final org.apache.thrift.protocol.TField PROCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("process", org.apache.thrift.protocol.TType.STRING, (short)2);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new setProcess_argsStandardSchemeFactory());
      schemes.put(TupleScheme.class, new setProcess_argsTupleSchemeFactory());
    }

    public String tmlId; // required
    public String process; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      TML_ID((short)1, "tmlId"),
      PROCESS((short)2, "process");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 1: // TML_ID
            return TML_ID;
          case 2: // PROCESS
            return PROCESS;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.TML_ID, new org.apache.thrift.meta_data.FieldMetaData("tmlId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
      tmpMap.put(_Fields.PROCESS, new org.apache.thrift.meta_data.FieldMetaData("process", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(setProcess_args.class, metaDataMap);
    }

    public setProcess_args() {
    }

    public setProcess_args(
      String tmlId,
      String process)
    {
      this();
      this.tmlId = tmlId;
      this.process = process;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public setProcess_args(setProcess_args other) {
      if (other.isSetTmlId()) {
        this.tmlId = other.tmlId;
      }
      if (other.isSetProcess()) {
        this.process = other.process;
      }
    }

    public setProcess_args deepCopy() {
      return new setProcess_args(this);
    }

    public void clear() {
      this.tmlId = null;
      this.process = null;
    }

    public String getTmlId() {
      return this.tmlId;
    }

    public setProcess_args setTmlId(String tmlId) {
      this.tmlId = tmlId;
      return this;
    }

    public void unsetTmlId() {
      this.tmlId = null;
    }

    /** Returns true if field tmlId is set (has been assigned a value) and false otherwise */
    public boolean isSetTmlId() {
      return this.tmlId != null;
    }

    public void setTmlIdIsSet(boolean value) {
      if (!value) {
        this.tmlId = null;
      }
    }

    public String getProcess() {
      return this.process;
    }

    public setProcess_args setProcess(String process) {
      this.process = process;
      return this;
    }

    public void unsetProcess() {
      this.process = null;
    }

    /** Returns true if field process is set (has been assigned a value) and false otherwise */
    public boolean isSetProcess() {
      return this.process != null;
    }

    public void setProcessIsSet(boolean value) {
      if (!value) {
        this.process = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case TML_ID:
        if (value == null) {
          unsetTmlId();
        } else {
          setTmlId((String)value);
        }
        break;

      case PROCESS:
        if (value == null) {
          unsetProcess();
        } else {
          setProcess((String)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case TML_ID:
        return getTmlId();

      case PROCESS:
        return getProcess();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case TML_ID:
        return isSetTmlId();
      case PROCESS:
        return isSetProcess();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof setProcess_args)
        return this.equals((setProcess_args)that);
      return false;
    }

    public boolean equals(setProcess_args that) {
      if (that == null)
        return false;

      boolean this_present_tmlId = true && this.isSetTmlId();
      boolean that_present_tmlId = true && that.isSetTmlId();
      if (this_present_tmlId || that_present_tmlId) {
        if (!(this_present_tmlId && that_present_tmlId))
          return false;
        if (!this.tmlId.equals(that.tmlId))
          return false;
      }

      boolean this_present_process = true && this.isSetProcess();
      boolean that_present_process = true && that.isSetProcess();
      if (this_present_process || that_present_process) {
        if (!(this_present_process && that_present_process))
          return false;
        if (!this.process.equals(that.process))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(setProcess_args other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      setProcess_args typedOther = (setProcess_args)other;

      lastComparison = Boolean.valueOf(isSetTmlId()).compareTo(typedOther.isSetTmlId());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetTmlId()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tmlId, typedOther.tmlId);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      lastComparison = Boolean.valueOf(isSetProcess()).compareTo(typedOther.isSetProcess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetProcess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.process, typedOther.process);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("setProcess_args(");
      boolean first = true;

      sb.append("tmlId:");
      if (this.tmlId == null) {
        sb.append("null");
      } else {
        sb.append(this.tmlId);
      }
      first = false;
      if (!first) sb.append(", ");
      sb.append("process:");
      if (this.process == null) {
        sb.append("null");
      } else {
        sb.append(this.process);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class setProcess_argsStandardSchemeFactory implements SchemeFactory {
      public setProcess_argsStandardScheme getScheme() {
        return new setProcess_argsStandardScheme();
      }
    }

    private static class setProcess_argsStandardScheme extends StandardScheme<setProcess_args> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, setProcess_args struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 1: // TML_ID
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.tmlId = iprot.readString();
                struct.setTmlIdIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            case 2: // PROCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.process = iprot.readString();
                struct.setProcessIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, setProcess_args struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.tmlId != null) {
          oprot.writeFieldBegin(TML_ID_FIELD_DESC);
          oprot.writeString(struct.tmlId);
          oprot.writeFieldEnd();
        }
        if (struct.process != null) {
          oprot.writeFieldBegin(PROCESS_FIELD_DESC);
          oprot.writeString(struct.process);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class setProcess_argsTupleSchemeFactory implements SchemeFactory {
      public setProcess_argsTupleScheme getScheme() {
        return new setProcess_argsTupleScheme();
      }
    }

    private static class setProcess_argsTupleScheme extends TupleScheme<setProcess_args> {

      public void write(org.apache.thrift.protocol.TProtocol prot, setProcess_args struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetTmlId()) {
          optionals.set(0);
        }
        if (struct.isSetProcess()) {
          optionals.set(1);
        }
        oprot.writeBitSet(optionals, 2);
        if (struct.isSetTmlId()) {
          oprot.writeString(struct.tmlId);
        }
        if (struct.isSetProcess()) {
          oprot.writeString(struct.process);
        }
      }

      public void read(org.apache.thrift.protocol.TProtocol prot, setProcess_args struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(2);
        if (incoming.get(0)) {
          struct.tmlId = iprot.readString();
          struct.setTmlIdIsSet(true);
        }
        if (incoming.get(1)) {
          struct.process = iprot.readString();
          struct.setProcessIsSet(true);
        }
      }
    }

  }

  public static class setProcess_result implements org.apache.thrift.TBase<setProcess_result, setProcess_result._Fields>, java.io.Serializable, Cloneable   {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("setProcess_result");

    private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField("success", org.apache.thrift.protocol.TType.STRING, (short)0);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    static {
      schemes.put(StandardScheme.class, new setProcess_resultStandardSchemeFactory());
      schemes.put(TupleScheme.class, new setProcess_resultTupleSchemeFactory());
    }

    public String success; // required

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
      SUCCESS((short)0, "success");

      private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

      static {
        for (_Fields field : EnumSet.allOf(_Fields.class)) {
          byName.put(field.getFieldName(), field);
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, or null if its not found.
       */
      public static _Fields findByThriftId(int fieldId) {
        switch(fieldId) {
          case 0: // SUCCESS
            return SUCCESS;
          default:
            return null;
        }
      }

      /**
       * Find the _Fields constant that matches fieldId, throwing an exception
       * if it is not found.
       */
      public static _Fields findByThriftIdOrThrow(int fieldId) {
        _Fields fields = findByThriftId(fieldId);
        if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
        return fields;
      }

      /**
       * Find the _Fields constant that matches name, or null if its not found.
       */
      public static _Fields findByName(String name) {
        return byName.get(name);
      }

      private final short _thriftId;
      private final String _fieldName;

      _Fields(short thriftId, String fieldName) {
        _thriftId = thriftId;
        _fieldName = fieldName;
      }

      public short getThriftFieldId() {
        return _thriftId;
      }

      public String getFieldName() {
        return _fieldName;
      }
    }

    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    static {
      Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
      tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success", org.apache.thrift.TFieldRequirementType.DEFAULT, 
          new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
      metaDataMap = Collections.unmodifiableMap(tmpMap);
      org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(setProcess_result.class, metaDataMap);
    }

    public setProcess_result() {
    }

    public setProcess_result(
      String success)
    {
      this();
      this.success = success;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public setProcess_result(setProcess_result other) {
      if (other.isSetSuccess()) {
        this.success = other.success;
      }
    }

    public setProcess_result deepCopy() {
      return new setProcess_result(this);
    }

    public void clear() {
      this.success = null;
    }

    public String getSuccess() {
      return this.success;
    }

    public setProcess_result setSuccess(String success) {
      this.success = success;
      return this;
    }

    public void unsetSuccess() {
      this.success = null;
    }

    /** Returns true if field success is set (has been assigned a value) and false otherwise */
    public boolean isSetSuccess() {
      return this.success != null;
    }

    public void setSuccessIsSet(boolean value) {
      if (!value) {
        this.success = null;
      }
    }

    public void setFieldValue(_Fields field, Object value) {
      switch (field) {
      case SUCCESS:
        if (value == null) {
          unsetSuccess();
        } else {
          setSuccess((String)value);
        }
        break;

      }
    }

    public Object getFieldValue(_Fields field) {
      switch (field) {
      case SUCCESS:
        return getSuccess();

      }
      throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
      if (field == null) {
        throw new IllegalArgumentException();
      }

      switch (field) {
      case SUCCESS:
        return isSetSuccess();
      }
      throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
      if (that == null)
        return false;
      if (that instanceof setProcess_result)
        return this.equals((setProcess_result)that);
      return false;
    }

    public boolean equals(setProcess_result that) {
      if (that == null)
        return false;

      boolean this_present_success = true && this.isSetSuccess();
      boolean that_present_success = true && that.isSetSuccess();
      if (this_present_success || that_present_success) {
        if (!(this_present_success && that_present_success))
          return false;
        if (!this.success.equals(that.success))
          return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return 0;
    }

    public int compareTo(setProcess_result other) {
      if (!getClass().equals(other.getClass())) {
        return getClass().getName().compareTo(other.getClass().getName());
      }

      int lastComparison = 0;
      setProcess_result typedOther = (setProcess_result)other;

      lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(typedOther.isSetSuccess());
      if (lastComparison != 0) {
        return lastComparison;
      }
      if (isSetSuccess()) {
        lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, typedOther.success);
        if (lastComparison != 0) {
          return lastComparison;
        }
      }
      return 0;
    }

    public _Fields fieldForId(int fieldId) {
      return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
      schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
      schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
      }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder("setProcess_result(");
      boolean first = true;

      sb.append("success:");
      if (this.success == null) {
        sb.append("null");
      } else {
        sb.append(this.success);
      }
      first = false;
      sb.append(")");
      return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
      // check for required fields
      // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
      try {
        write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
      try {
        read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
      } catch (org.apache.thrift.TException te) {
        throw new java.io.IOException(te);
      }
    }

    private static class setProcess_resultStandardSchemeFactory implements SchemeFactory {
      public setProcess_resultStandardScheme getScheme() {
        return new setProcess_resultStandardScheme();
      }
    }

    private static class setProcess_resultStandardScheme extends StandardScheme<setProcess_result> {

      public void read(org.apache.thrift.protocol.TProtocol iprot, setProcess_result struct) throws org.apache.thrift.TException {
        org.apache.thrift.protocol.TField schemeField;
        iprot.readStructBegin();
        while (true)
        {
          schemeField = iprot.readFieldBegin();
          if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
            break;
          }
          switch (schemeField.id) {
            case 0: // SUCCESS
              if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                struct.success = iprot.readString();
                struct.setSuccessIsSet(true);
              } else { 
                org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
              }
              break;
            default:
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
          }
          iprot.readFieldEnd();
        }
        iprot.readStructEnd();

        // check for required fields of primitive type, which can't be checked in the validate method
        struct.validate();
      }

      public void write(org.apache.thrift.protocol.TProtocol oprot, setProcess_result struct) throws org.apache.thrift.TException {
        struct.validate();

        oprot.writeStructBegin(STRUCT_DESC);
        if (struct.success != null) {
          oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
          oprot.writeString(struct.success);
          oprot.writeFieldEnd();
        }
        oprot.writeFieldStop();
        oprot.writeStructEnd();
      }

    }

    private static class setProcess_resultTupleSchemeFactory implements SchemeFactory {
      public setProcess_resultTupleScheme getScheme() {
        return new setProcess_resultTupleScheme();
      }
    }

    private static class setProcess_resultTupleScheme extends TupleScheme<setProcess_result> {

      public void write(org.apache.thrift.protocol.TProtocol prot, setProcess_result struct) throws org.apache.thrift.TException {
        TTupleProtocol oprot = (TTupleProtocol) prot;
        BitSet optionals = new BitSet();
        if (struct.isSetSuccess()) {
          optionals.set(0);
        }
        oprot.writeBitSet(optionals, 1);
        if (struct.isSetSuccess()) {
          oprot.writeString(struct.success);
        }
      }

      public void read(org.apache.thrift.protocol.TProtocol prot, setProcess_result struct) throws org.apache.thrift.TException {
        TTupleProtocol iprot = (TTupleProtocol) prot;
        BitSet incoming = iprot.readBitSet(1);
        if (incoming.get(0)) {
          struct.success = iprot.readString();
          struct.setSuccessIsSet(true);
        }
      }
    }

  }

}