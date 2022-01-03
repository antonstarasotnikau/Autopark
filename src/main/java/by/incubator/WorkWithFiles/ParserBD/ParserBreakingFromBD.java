package by.incubator.WorkWithFiles.ParserBD;

import by.incubator.Service.OrdersService;
import by.incubator.WorkWithFiles.IParserBreakingFromFile;
import by.incubator.infrastructure.core.annotations.Autowired;

public class ParserBreakingFromBD implements IParserBreakingFromFile {
    @Autowired
    private OrdersService ordersService;
}
