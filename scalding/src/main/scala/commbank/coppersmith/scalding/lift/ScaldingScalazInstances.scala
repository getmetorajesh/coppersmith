//
// Copyright 2016 Commonwealth Bank of Australia
//
//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at
//        http://www.apache.org/licenses/LICENSE-2.0
//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//

package commbank.coppersmith.scalding.lift

import com.twitter.scalding.typed.TypedPipe

import scalaz.Functor

/**
  * There is good  reason to include these somewhere more central to omnia, and soon.
  */
object ScaldingScalazInstances {
  implicit val typedPipeFunctor: Functor[TypedPipe] = new Functor[TypedPipe] {
    override def map[A, B](fa: TypedPipe[A])(f: (A) => B): TypedPipe[B] =
      fa.map(f)
  }
}
