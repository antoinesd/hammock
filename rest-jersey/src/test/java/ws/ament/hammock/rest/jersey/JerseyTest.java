/*
 * Copyright 2016 Hammock and its contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ws.ament.hammock.rest.jersey;

import java.net.URI;

import javax.enterprise.inject.spi.Extension;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import ws.ament.hammock.web.spi.StartWebServer;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.is;

@RunWith(Arquillian.class)
public class JerseyTest {

    @Deployment
    public static JavaArchive createArchive() {
        return ShrinkWrap.create(JavaArchive.class).addClasses(RestController.class, RestApp.class)
                .addAsServiceProviderAndClasses(Extension.class, StartWebServer.class);
    }

    @ArquillianResource
    private URI uri;

    @Test
    public void shouldBeAbleToRetrieveRestEndpoint() throws Exception {
        System.out.println("Jersey1Test::shouldBeAbleToRetrieveRestEndpoint");
        get(uri + "/rest").then()
                .assertThat().statusCode(200)
                .body(is("Hello, World!"));
    }
}