package com.android.expandablelistview;

import java.util.List;

/**
 * Created by 别乱动 on 2018/2/25.
 */

public class Channel {
    private List<String> numbers;
    private String group;

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        Channel channel = (Channel) o;
        return this.getGroup().equals(channel.getGroup());
    }
}
