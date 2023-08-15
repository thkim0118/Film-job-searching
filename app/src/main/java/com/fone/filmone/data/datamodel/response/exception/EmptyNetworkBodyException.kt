package com.fone.filmone.data.datamodel.response.exception

import com.fone.filmone.domain.model.common.DataFail

class EmptyNetworkBodyException(val dataFail: DataFail) : Throwable()
