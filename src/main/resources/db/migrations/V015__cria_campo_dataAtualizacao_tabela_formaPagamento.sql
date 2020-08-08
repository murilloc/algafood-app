alter table forma_pagamento
    add data_atualizacao DATETIME not null;

update forma_pagamento set forma_pagamento.data_atualizacao = utc_timestamp;

alter table forma_pagamento
    modify data_atualizacao DATETIME not null;