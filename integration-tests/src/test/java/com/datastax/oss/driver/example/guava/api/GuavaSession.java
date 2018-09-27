/*
 * Copyright DataStax, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.datastax.oss.driver.example.guava.api;

import com.datastax.oss.driver.api.core.cql.AsyncResultSet;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.Statement;
import com.datastax.oss.driver.api.core.session.Session;
import com.datastax.oss.driver.api.core.type.reflect.GenericType;
import com.datastax.oss.driver.internal.core.cql.DefaultPrepareRequest;
import com.google.common.util.concurrent.ListenableFuture;

public interface GuavaSession extends Session {

  GenericType<ListenableFuture<AsyncResultSet<Row>>> ASYNC =
      new GenericType<ListenableFuture<AsyncResultSet<Row>>>() {};

  GenericType<ListenableFuture<PreparedStatement>> ASYNC_PREPARED =
      new GenericType<ListenableFuture<PreparedStatement>>() {};

  default ListenableFuture<AsyncResultSet<Row>> executeAsync(Statement<?> statement) {
    return this.execute(statement, ASYNC);
  }

  default ListenableFuture<AsyncResultSet<Row>> executeAsync(String statement) {
    return this.executeAsync(SimpleStatement.newInstance(statement));
  }

  default ListenableFuture<PreparedStatement> prepareAsync(SimpleStatement statement) {
    return this.execute(new DefaultPrepareRequest(statement), ASYNC_PREPARED);
  }

  default ListenableFuture<PreparedStatement> prepareAsync(String statement) {
    return this.prepareAsync(SimpleStatement.newInstance(statement));
  }
}
