package com.cvGateway.cvGateway.cybervision.model;

import lombok.Data;

/**
 * {@code PresetNetworkFilter}.
 *
 * <p>PresetNetworkFilter explicit how a Vlan and/or Vlan will be used as filter in a Preset
 *
 * <p>프리셋의 네트워크 필터.
 */
@Data
public class PresetNetworkFilter {
    /** {@code operator} */
    private String operator;

    /** {@code value} */
    private PresetNetwork value;
}
