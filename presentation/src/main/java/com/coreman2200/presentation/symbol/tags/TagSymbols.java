package com.coreman2200.presentation.symbol.tags;

import com.coreman2200.domain.symbol.SymbolStrata;
import com.coreman2200.presentation.symbol.EntityStrata;
import com.coreman2200.domain.symbol.symbolinterface.ISymbol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * TagSymbols
 * Simple test tags to test functionality of Tags/tagging systems before implementation is designed..
 *
 * Created by Cory Higginbottom on 11/3/15
 * http://github.com/coreman2200
 *
 * Licensed under the GNU General Public License (GPL), Version 2.0.
 * You may not use this file except in compliance with this License.
 *
 * You may obtain a copy of the GPLv2 License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

public enum TagSymbols implements ITagSymbol {
    VENUS, MARS, JUPITER, SATURN, URANUS, NEPTUNE, PLUTO, RETROGRADE, ARIES,
    TAURUS, GEMINI, CANCER, LEO, VIRGO, LIBRA, SCORPIO, SAGITTARIUS, CAPRICORN,
    AQUARIUS, PISCES, FIRE, AIR, WATER, VOID, ZERO, ONE, TWO, THREE,
    FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, LIFE_JOURNEY, TALENTS, DESTINY,
    INFLUENCE, PERSONALITY, LESSONS, THINKING, TWENTY__SEVEN_YEARS, ORIENTATION,
    SELF_EXPRESSION, EARLY_STAGES, MIDDLE_STAGES, PERSISTENT, LATER_STAGES,
    ANNUAL, DAILY, MONTHLY, EVEN, ODD, SUN, MOON, MERCURY,


    ACHIEVEMENT, ABILITY, ACTION, ACTIVITY, ADAPTABILITY, ADVENTUROUS,
    AGGRESSION, ALL, ALTRUISM, AMBITION, APPLICATION, ASSERTION, ATTAINMENT,
    ATTRACTIONS, AUTHORITY, AWARENESS, BALANCE, BEAUTY, BEGINNING, BEING,
    BOUNDARY, BUILDING, BUSINESS, CAREER, CENTER, CHALLENGE, CHANGES,
    CHILDREN, CHOICE, COMMITMENT, COMMUNICATION, COMPASSION, COMPLETION,
    CONCENTRATION, CONCLUSION, CONNECTION, CONSOLIDATION, CONTRACTS, CONTRAST,
    CREATION, CREATIVITY, CURIOSITY, CYCLE, DARKNESS, DEFENSE_MECHANISM, DENIAL,
    DEED, DEPTH, DESIRE, DETERMINATION, DISCIPLINE, DISCOVERY, DIVERSITY,
    DIVINATION, DOMESTIC_ISSUES, DOMINANCE, DUALITY, EARTH, EDUCATION, EGO,
    ELEGANCE, EMOTION, ENERGY, ENLIGHTENMENT, ETHICS, EVALUATION, EXPANSION,
    EXPECTATIONS, EXPLORATION, EXPRESSION, EXTREMES, FAIRNESS, FAITH, FEAR,
    FERTILITY, FIRST_IMPRESSION, FOCUS, FOUNDATION, FREEDOM, FREE__SPIRIT,
    GAIN, GATEWAY, GOALS, GREATNESS, GROUPS, GROWTH, HABITS, HARMONY, HEALTH,
    HUMANITY, ILLUSION, IMAGINATION, INDIVIDUALITY, INNER_GOAL, INNOVATION,
    INSIGHT, INSPIRATION, INTELLIGENCE, INTERPRETATION, INTIMACY, INTUITION,
    IRRATIONAL, ISOLATION, JOURNEY, JUSTICE, KINDNESS, KNOWLEDGE, LEADERSHIP,
    LOSS, LOVE, MARRIAGE, MASTER_NUMBER, MATERIALISM, MATURITY, MORALITY,
    MOTHERHOOD, MOTIVATION, MOVEMENT, MYSTERY, MYSTICISM, NURTURING, OPPOSITION,
    OPTIMISM, ORGANIZING, PARTNERS, PASSION, PHYSICALITY, PLEASURE, POSITION,
    POWER, PRACTICALITY, PRIDE, PROCESS, PROGRESSION, PROSPERITY, PROTECTING,
    PURPOSE, RAISED_CONSCIOUSNESS, RARE, RATIONALITY, REALITY, REASON, REBIRTH,
    RECHARGING, RECLAMATION, RENEWAL, REPUTATION, RESPONSIBILITY, RESTRICTION,
    REVOLUTIONARY, RHYTHM, ROMANCE, SEDUCTION, SELF__IMAGE, SENSITIVITY,
    SEXUALITY, SHARING, SOCIAL_STATUS, SPARK, SPIRITUALITY, STABILITY,
    STRENGTH, STRUCTURE, SUFFERING, TANGIBILITY, TASTES, TRANSFORMATION,
    TRAVEL, TRUTH, UNDERSTANDING, UNIQUE, UNITY, UPBRINGING, VALUES, VISION,
    VITALITY, WHOLENESS, WISDOM, WORK, WORLDLY, THE_WOUNDED_HEALER, WRITING;

    public int size() { return 1; }

    public SymbolStrata symbolStrata() { return SymbolStrata.ENTITY; }

    public EntityStrata getEntityID() { return EntityStrata.TAG; }

    public Enum<? extends Enum<?>> symbolID() { return this; }

    @Override
    public Enum<? extends Enum<?>> symbolType() {
        return getEntityID();
    }

    // TODO: Poor dependency
    public final Collection<Enum<? extends Enum<?>>> symbolIDCollection() {
        Collection<Enum<? extends Enum<?>>> symbolIDs = new ArrayList<>();
        symbolIDs.add(symbolID());
        return symbolIDs;
    }

    public final Map<EntityStrata, ITagSymbol> produceSymbol() {
        HashMap<EntityStrata, ITagSymbol> map = new HashMap<>();
        map.put(getEntityID(), this);
        return map;
    }

    @Override
    public Collection<TagSymbols> getQualities() {
        Collection<TagSymbols> tags = new ArrayList<>();
        tags.add(this);
        return tags;
    }

    @Override
    public int getTagCount(TagSymbols tag) {
        return 1;
    }

    public void testGenerateLogs() { }

    @Override
    public boolean containsSymbol(ISymbol symbol) {
        return (symbol instanceof ITagSymbol);
    }

    @Override
    public String toString() {
        return name().replaceAll("__","-").replaceAll("_", " ").toLowerCase(Locale.US);
    }

    static public TagSymbols getTagForString(String name) {
        return valueOf(name.replaceAll("-", "__").replaceAll(" ", "_").toUpperCase(Locale.US));
    }

}
