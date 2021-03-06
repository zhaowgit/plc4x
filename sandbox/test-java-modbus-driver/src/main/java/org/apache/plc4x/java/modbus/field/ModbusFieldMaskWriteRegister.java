/*
 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */
package org.apache.plc4x.java.modbus.field;

import org.apache.plc4x.java.api.exceptions.PlcInvalidFieldException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is not really an address, but a different type of operation.
 * Perhaps in the future we will use this in order to send optimized requests,
 * but for now it shouldn't be used and is just implemented for the sake of
 * completeness.
 */
@Deprecated
public class ModbusFieldMaskWriteRegister extends ModbusField {

    public static final Pattern ADDRESS_PATTERN = Pattern.compile("maskwrite:" + ModbusField.ADDRESS_PATTERN + "/" + "(?<andMask>\\d+)/(?<orMask>\\d+)");

    private final int andMask;
    private final int orMask;

    protected ModbusFieldMaskWriteRegister(int address, int andMask, int orMask, Integer quantity) {
        super(address, quantity);
        this.andMask = andMask;
        this.orMask = orMask;
    }

    public static ModbusFieldMaskWriteRegister of(String addressString) throws PlcInvalidFieldException {
        Matcher matcher = ADDRESS_PATTERN.matcher(addressString);
        if (!matcher.matches()) {
            throw new PlcInvalidFieldException(addressString, ADDRESS_PATTERN);
        }
        int address = Integer.parseInt(matcher.group("address"));
        int andMask = Integer.parseInt(matcher.group("andMask"));
        int orMask = Integer.parseInt(matcher.group("orMask"));

        String quantityString = matcher.group("quantity");
        Integer quantity = quantityString != null ? Integer.valueOf(quantityString) : null;
        return new ModbusFieldMaskWriteRegister(address, andMask, orMask, quantity);
    }

    public int getAndMask() {
        return andMask;
    }

    public int getOrMask() {
        return orMask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModbusFieldMaskWriteRegister)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        ModbusFieldMaskWriteRegister that = (ModbusFieldMaskWriteRegister) o;
        return andMask == that.andMask &&
            orMask == that.orMask;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), andMask, orMask);
    }

    @Override
    public String toString() {
        return "MaskWriteRegisterModbusField{" +
            "andMask=" + andMask +
            ", orMask=" + orMask +
            "} " + super.toString();
    }

}
