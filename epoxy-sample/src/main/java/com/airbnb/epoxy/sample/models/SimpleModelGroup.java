package com.airbnb.epoxy.sample.models;

import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyModelGroup;
import com.airbnb.epoxy.sample.R;

import java.util.Collection;

public class SimpleModelGroup extends EpoxyModelGroup {
  
  public SimpleModelGroup(Collection<? extends EpoxyModel<?>> models) {
    super(R.layout.simple_model_group, models);
  }
}
