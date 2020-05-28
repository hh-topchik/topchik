import React, { Fragment } from 'react';
import classNames from 'classnames';
import './styles.less';
import {
    LineChart,
    Line,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    ResponsiveContainer,
} from 'recharts';

export default function Chart({ data, activeCategoryId, dataKeyX, dataKeyY }) {
    const currentData = data.filter((item) => item.categoryId == activeCategoryId);

    return (
        <div className="chart">
            {currentData.length === 0 ? (
                <div>Данные по выбранной категории отсутствуют</div>
            ) : (
                <ResponsiveContainer>
                    <LineChart
                        data={currentData[0].date}
                        margin={{ top: 5, right: 30, left: 0, bottom: 5 }}
                    >
                        <XAxis dataKey={dataKeyX} />
                        <YAxis />
                        <Tooltip content={<CustomTooltip />} />
                        <Line
                            type="monotone"
                            dataKey={dataKeyY}
                            stroke="#0f8fee"
                            activeDot={{ r: 8 }}
                        />
                    </LineChart>
                </ResponsiveContainer>
            )}
        </div>
    );
}

function CustomTooltip(props) {
    const { active, payload } = props;
    return active ? (
        <div className="custom-tooltip">
            <div className="custom-tooltip__date">{payload[0].payload.week}</div>
            <div className="custom-tooltip__count">{payload[0].payload.value}</div>
            {payload[0].payload.medal === 0 ? null : (
                <i
                    className={classNames('fas fa-medal custom-tooltip__medal', {
                        gold: payload[0].payload.medal === 1,
                        silver: payload[0].payload.medal === 2,
                        bronze: payload[0].payload.medal === 3,
                    })}
                ></i>
            )}
        </div>
    ) : null;
}
