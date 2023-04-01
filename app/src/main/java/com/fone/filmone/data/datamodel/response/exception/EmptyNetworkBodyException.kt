package com.fone.filmone.data.datamodel.response.exception

import com.fone.filmone.data.datamodel.response.common.NetworkFail

class EmptyNetworkBodyException(val networkFail: NetworkFail) : Throwable()