package com.example.demo.test;

import org.springframework.batch.item.ItemProcessor;

import com.example.demo.vo.Sample;

//processor - name에다가 '_test' 추가해줌
public class itemSample implements ItemProcessor<Sample, Sample> {

	@Override
	public Sample process(Sample item) throws Exception {
		
		System.out.println("item: " + item);
		
		Sample newItem = new Sample();
		
		newItem.setId(item.getId());
		newItem.setName(item.getName() + "_test");
		
		System.out.println("newItem: " + newItem);
		
		return newItem;
	}

}
