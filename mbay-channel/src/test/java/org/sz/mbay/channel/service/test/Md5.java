package org.sz.mbay.channel.service.test;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.sz.mbay.common.util.DigestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
public class Md5 extends TestCase {
	
	@Test
	public void test() {
		System.out.println(DigestUtils.md5Encrypt("201503031101006209DD586FF111115251778128"));
	}
}
